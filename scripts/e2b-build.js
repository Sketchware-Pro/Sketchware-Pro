#!/usr/bin/env node

const { execFileSync } = require("child_process");
const { createReadStream, existsSync, readFileSync, writeFileSync } = require("fs");
const { createRequire } = require("module");
const path = require("path");

const repoRoot = path.resolve(__dirname, "..");
const defaultTemplate = "sketchware-android-build-codex:cpu8";
const defaultArchive = "/tmp/sketchware-pro-e2b-build.tar.gz";
const remoteArchive = "/home/user/sketchware-pro-e2b-build.tar.gz";
const defaultSandboxIdFile = path.join(repoRoot, ".e2b-sandbox-id");

function parseArgs(argv) {
  const options = {
    archive: process.env.E2B_BUILD_ARCHIVE || defaultArchive,
    forceNew: process.env.E2B_FORCE_NEW === "1",
    killAfter: process.env.E2B_KILL_AFTER === "1",
    sandboxId: process.env.E2B_SANDBOX_ID || "",
    sandboxIdFile: process.env.E2B_SANDBOX_ID_FILE || defaultSandboxIdFile,
    tailLines: Number(process.env.E2B_TAIL_LINES || 400),
    template: process.env.E2B_TEMPLATE || defaultTemplate,
    timeoutMs: Number(process.env.E2B_TIMEOUT_MS || 60 * 60 * 1000),
  };

  for (let index = 0; index < argv.length; index += 1) {
    const arg = argv[index];
    if (arg === "--archive") options.archive = argv[++index];
    else if (arg === "--force-new") options.forceNew = true;
    else if (arg === "--kill-after") options.killAfter = true;
    else if (arg === "--sandbox-id") options.sandboxId = argv[++index];
    else if (arg === "--sandbox-id-file") options.sandboxIdFile = argv[++index];
    else if (arg === "--tail-lines") options.tailLines = Number(argv[++index]);
    else if (arg === "--template") options.template = argv[++index];
    else if (arg === "--timeout-ms") options.timeoutMs = Number(argv[++index]);
    else if (arg === "--help" || arg === "-h") {
      printHelp();
      process.exit(0);
    } else {
      throw new Error(`Unknown argument: ${arg}`);
    }
  }

  if (!Number.isFinite(options.tailLines) || options.tailLines < 1) {
    throw new Error("--tail-lines must be a positive number");
  }
  if (!Number.isFinite(options.timeoutMs) || options.timeoutMs < 1000) {
    throw new Error("--timeout-ms must be at least 1000");
  }

  return options;
}

function printHelp() {
  console.log(`Usage: node scripts/e2b-build.js [options]

Options:
  --template <name>     E2B template to use (default: ${defaultTemplate})
  --sandbox-id <id>     Reuse this running sandbox instead of creating one
  --sandbox-id-file <p> File used to persist sandbox id (default: .e2b-sandbox-id)
  --force-new          Create a new sandbox and overwrite the sandbox id file
  --kill-after         Kill the sandbox after the build instead of keeping it warm
  --tail-lines <count>  Lines to keep from stdout/stderr tails (default: 400)
  --archive <path>      Local tarball path (default: ${defaultArchive})
  --timeout-ms <ms>     Sandbox and build timeout (default: 3600000)

Environment:
  E2B_API_KEY can be exported or stored in .env.e2b.local.
  E2B_SANDBOX_ID can point to an existing sandbox to reuse.
`);
}

function loadLocalEnv() {
  const envPath = path.join(repoRoot, ".env.e2b.local");
  if (!existsSync(envPath)) return;

  const content = readFileSync(envPath, "utf8");
  for (const line of content.split(/\r?\n/)) {
    const trimmed = line.trim();
    if (!trimmed || trimmed.startsWith("#")) continue;
    const separator = trimmed.indexOf("=");
    if (separator === -1) continue;
    const key = trimmed.slice(0, separator).trim();
    const value = trimmed.slice(separator + 1).trim().replace(/^["']|["']$/g, "");
    if (key && process.env[key] === undefined) process.env[key] = value;
  }
}

function requireE2B() {
  try {
    return require("e2b");
  } catch (localError) {
    try {
      return createRequire("/root/nusantaraai/package.json")("e2b");
    } catch {
      throw localError;
    }
  }
}

function shellQuote(value) {
  return `'${String(value).replace(/'/g, "'\\''")}'`;
}

function tail(text, lines) {
  const value = String(text || "");
  return value.split("\n").slice(-lines).join("\n");
}

function makeArchive(archive) {
  execFileSync(
    "tar",
    [
      "--exclude=sketchware-pro/.git",
      "--exclude=sketchware-pro/.gradle",
      "--exclude=sketchware-pro/build",
      "--exclude=sketchware-pro/app/build",
      "-czf",
      archive,
      "-C",
      path.dirname(repoRoot),
      path.basename(repoRoot),
    ],
    { stdio: "inherit" },
  );
}

function readSandboxId(options) {
  if (options.forceNew) return "";
  if (options.sandboxId) return options.sandboxId.trim();
  if (!existsSync(options.sandboxIdFile)) return "";

  return readFileSync(options.sandboxIdFile, "utf8").trim();
}

async function getSandbox(Sandbox, options) {
  const existingSandboxId = readSandboxId(options);
  if (existingSandboxId) {
    try {
      const sandbox = await Sandbox.connect(existingSandboxId, {
        apiKey: process.env.E2B_API_KEY,
        timeoutMs: options.timeoutMs,
      });
      console.log(`E2B_REUSED_SANDBOX_ID=${sandbox.sandboxId}`);
      return sandbox;
    } catch (error) {
      console.warn(`E2B_REUSE_FAILED=${existingSandboxId}`);
      console.warn(error && error.message ? error.message : error);
      if (options.sandboxId) throw error;
    }
  }

  const sandbox = await Sandbox.create(options.template, {
    apiKey: process.env.E2B_API_KEY,
    timeoutMs: options.timeoutMs,
  });
  writeFileSync(options.sandboxIdFile, `${sandbox.sandboxId}\n`, { mode: 0o600 });
  console.log(`E2B_CREATED_SANDBOX_ID=${sandbox.sandboxId}`);
  console.log(`E2B_SANDBOX_ID_FILE=${options.sandboxIdFile}`);
  return sandbox;
}

async function main() {
  const options = parseArgs(process.argv.slice(2));
  loadLocalEnv();

  if (!process.env.E2B_API_KEY) {
    throw new Error("Missing E2B_API_KEY. Put it in .env.e2b.local or export it.");
  }

  const { Sandbox } = requireE2B();
  makeArchive(options.archive);

  const sandbox = await getSandbox(Sandbox, options);
  console.log(`E2B_SANDBOX_ID=${sandbox.sandboxId}`);
  console.log(`E2B_TEMPLATE=${options.template}`);

  const buildSteps = [
    "set -eu",
    "source /etc/profile.d/android-build.sh",
    "sudo mkdir -p /etc/ssl/certs/java",
    "sudo /var/lib/dpkg/info/ca-certificates-java.postinst configure >/dev/null",
    "sudo chown -R user:user /opt/android-sdk",
    'sdkmanager "build-tools;35.0.0" >/dev/null',
    "rm -rf /home/user/workspace/sketchware-pro",
    "mkdir -p /home/user/workspace",
    `tar -xzf ${shellQuote(remoteArchive)} -C /home/user/workspace`,
    "cd /home/user/workspace/sketchware-pro",
    "chmod +x ./gradlew",
    'printf "sdk.dir=%s\\n" "$ANDROID_HOME" > local.properties',
    'printf "PWD=%s\\n" "$PWD"',
    "nproc",
    "free -m",
    "java -version",
    "./gradlew assembleDebug --no-daemon --stacktrace --max-workers=2",
  ];

  try {
    await sandbox.files.write(remoteArchive, createReadStream(options.archive));
    const result = await sandbox.commands.run(`bash -lc ${shellQuote(buildSteps.join(" && "))}`, {
      timeoutMs: options.timeoutMs,
    });

    console.log("BUILD_STDOUT_TAIL_START");
    console.log(tail(result.stdout, options.tailLines));
    console.log("BUILD_STDOUT_TAIL_END");
    if (result.stderr) {
      console.log("BUILD_STDERR_TAIL_START");
      console.log(tail(result.stderr, options.tailLines));
      console.log("BUILD_STDERR_TAIL_END");
    }
    console.log("BUILD_EXIT_CODE=0");
  } catch (error) {
    console.log("BUILD_FAILED");
    if (error.stdout) {
      console.log("BUILD_STDOUT_TAIL_START");
      console.log(tail(error.stdout, options.tailLines));
      console.log("BUILD_STDOUT_TAIL_END");
    }
    if (error.stderr) {
      console.log("BUILD_STDERR_TAIL_START");
      console.log(tail(error.stderr, options.tailLines));
      console.log("BUILD_STDERR_TAIL_END");
    }
    throw error;
  } finally {
    if (options.killAfter) {
      await sandbox.kill().catch(() => {});
    }
  }
}

main().catch((error) => {
  console.error(error && error.message ? error.message : error);
  process.exit(1);
});

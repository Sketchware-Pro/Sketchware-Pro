#!/usr/bin/env node

const { existsSync, readFileSync } = require("fs");
const { createRequire } = require("module");
const path = require("path");

const repoRoot = path.resolve(__dirname, "..");
const defaultTemplateName = "sketchware-android-build-codex:cpu8";

function parseArgs(argv) {
  const options = {
    cpuCount: Number(process.env.E2B_TEMPLATE_CPU_COUNT || 8),
    memoryMB: Number(process.env.E2B_TEMPLATE_MEMORY_MB || 8192),
    name: process.env.E2B_TEMPLATE_NAME || defaultTemplateName,
  };

  for (let index = 0; index < argv.length; index += 1) {
    const arg = argv[index];
    if (arg === "--cpu-count") options.cpuCount = Number(argv[++index]);
    else if (arg === "--memory-mb") options.memoryMB = Number(argv[++index]);
    else if (arg === "--name") options.name = argv[++index];
    else if (arg === "--help" || arg === "-h") {
      printHelp();
      process.exit(0);
    } else {
      throw new Error(`Unknown argument: ${arg}`);
    }
  }

  if (!Number.isFinite(options.cpuCount) || options.cpuCount < 1) {
    throw new Error("--cpu-count must be a positive number");
  }
  if (!Number.isFinite(options.memoryMB) || options.memoryMB < 1024) {
    throw new Error("--memory-mb must be at least 1024");
  }

  return options;
}

function printHelp() {
  console.log(`Usage: node scripts/e2b-build-template.js [options]

Options:
  --name <template>     Template name/tag to build (default: ${defaultTemplateName})
  --cpu-count <count>   CPU count for sandboxes created from this template (default: 8)
  --memory-mb <mb>      Memory for sandboxes created from this template (default: 8192)

Environment:
  E2B_API_KEY can be exported or stored in .env.e2b.local.
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

async function main() {
  loadLocalEnv();
  const options = parseArgs(process.argv.slice(2));

  if (!process.env.E2B_API_KEY) {
    throw new Error("Missing E2B_API_KEY. Put it in .env.e2b.local or export it.");
  }

  const { Template, defaultBuildLogger } = requireE2B();
  const template = Template()
    .fromUbuntuImage("24.04")
    .runCmd("apt-get update && DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends openjdk-17-jdk-headless ca-certificates curl unzip sudo && rm -rf /var/lib/apt/lists/*", { user: "root" })
    .runCmd("mkdir -p /opt/android-sdk/cmdline-tools /etc/ssl/certs/java && /var/lib/dpkg/info/ca-certificates-java.postinst configure >/dev/null || true", { user: "root" })
    .runCmd("curl -fsSL https://dl.google.com/android/repository/commandlinetools-linux-13114758_latest.zip -o /tmp/android-commandlinetools.zip && unzip -q /tmp/android-commandlinetools.zip -d /opt/android-sdk/cmdline-tools && mv /opt/android-sdk/cmdline-tools/cmdline-tools /opt/android-sdk/cmdline-tools/latest && rm /tmp/android-commandlinetools.zip", { user: "root" })
    .runCmd("yes | /opt/android-sdk/cmdline-tools/latest/bin/sdkmanager --sdk_root=/opt/android-sdk --licenses >/dev/null", { user: "root" })
    .runCmd("/opt/android-sdk/cmdline-tools/latest/bin/sdkmanager --sdk_root=/opt/android-sdk \"platform-tools\" \"platforms;android-36\" \"build-tools;36.0.0\" \"build-tools;35.0.0\" >/dev/null", { user: "root" })
    .runCmd("useradd -m -s /bin/bash user || true && echo 'user ALL=(ALL) NOPASSWD:ALL' >/etc/sudoers.d/e2b-user && chmod 0440 /etc/sudoers.d/e2b-user && chown -R user:user /opt/android-sdk", { user: "root" })
    .runCmd("printf '%s\\n' 'export ANDROID_HOME=/opt/android-sdk' 'export ANDROID_SDK_ROOT=/opt/android-sdk' 'export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64' 'export PATH=$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools:$JAVA_HOME/bin:$PATH' >/etc/profile.d/android-build.sh", { user: "root" })
    .setWorkdir("/home/user");

  console.log(`Building E2B template ${options.name} (${options.cpuCount} CPU, ${options.memoryMB} MB)...`);
  const info = await Template.build(template, options.name, {
    cpuCount: options.cpuCount,
    memoryMB: options.memoryMB,
    onBuildLogs: defaultBuildLogger(),
  });

  console.log("E2B_TEMPLATE_BUILD_COMPLETE");
  console.log(JSON.stringify(info, null, 2));
}

main().catch((error) => {
  console.error(error && error.message ? error.message : error);
  process.exit(1);
});

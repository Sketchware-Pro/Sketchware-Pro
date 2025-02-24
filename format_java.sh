# Format an java file with aosp code style.

CACHE_DIR="$HOME/.cache/trindadedev/formatters"

# Java Formatter Vars
JAVA_FORMATTER_DIR="$CACHE_DIR/google-java-format.jar"
JAVA_FORMATTER_VERSION="1.25.2"
JAVA_FORMATTER_URL="https://github.com/google/google-java-format/releases/download/v$JAVA_FORMATTER_VERSION/google-java-format-$JAVA_FORMATTER_VERSION-all-deps.jar"

mkdir -p "$CACHE_DIR"

FILE_TO_FORMAT="$1"

# download a formatter if not exists
download_formatter_if_not_present() {
  local formatter_dir="$1"
  local formatter_url="$2"
  local formatter_name="$3"

  if [ ! -f "$formatter_dir" ]; then
    echo "Downloading $formatter_name..."
    wget -q "$formatter_url" -O "$formatter_dir"
  fi
}

download_formatter_if_not_present "$JAVA_FORMATTER_DIR" "$JAVA_FORMATTER_URL" "Google Java Formatter"
echo "Formatting..."
java -jar "$JAVA_FORMATTER_DIR" --aosp --replace $FILE_TO_FORMAT
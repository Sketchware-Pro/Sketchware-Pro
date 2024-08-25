from telethon import TelegramClient
import os

# Telegram API credentials
api_id = int(os.getenv("API_ID"))
api_hash = os.getenv("API_HASH")
bot_token = os.getenv("BOT_TOKEN")
group_id = int(os.getenv("CHAT_ID"))

# File to send
file_path = os.getenv("FILE_PATH")

# Create the client with bot token directly
os.remove('bot_session.session') if os.path.exists('bot_session.session') else None
client = TelegramClient('bot_session', api_id, api_hash).start(bot_token=bot_token)

def human_readable_size(size, decimal_places=2):
    for unit in ['B', 'KB', 'MB', 'GB', 'TB']:
        if size < 1024.0:
            break
        size /= 1024.0
    return f"{size:.{decimal_places}f} {unit}"

# Total file size for progress tracking
total_size = os.path.getsize(file_path)
total_size_readable = human_readable_size(total_size)

async def progress(current, total):
    progress_percentage = (current / total_size) * 100
    uploaded_size_readable = human_readable_size(current)
    print(f"Uploaded {uploaded_size_readable} of {total_size_readable} - {progress_percentage:.2f}%")

async def send_file():
    if not os.path.exists(file_path):
        print("File not found")
        return
    
    print(f"Sending file: {file_path} to Telegram channel: {group_id}")

    # Send the file to the channel with progress callback
    await client.send_file(
        entity=group_id, 
        file=file_path, 
        caption=os.getenv("DESCRIPTION"),
        progress_callback=progress,
        reply_to=int(os.getenv("TOPIC_ID"))
    )
    print("\nFile sent successfully")

# Run the async function
with client:
    client.loop.run_until_complete(send_file())

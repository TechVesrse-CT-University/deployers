import sys
from gtts import gTTS
import os

if len(sys.argv) != 4:
    print("ERROR: Usage: python generate_audio.py <text_file> <lang> <output_path>")
    sys.exit(1)

text_file = sys.argv[1]
lang = sys.argv[2]
output_path = sys.argv[3]

if not os.path.exists(text_file):
    print(f"ERROR: Text file not found: {text_file}")
    sys.exit(1)

with open(text_file, "r", encoding="utf-8") as f:
    text = f.read()

try:
    tts = gTTS(text=text, lang=lang)
    os.makedirs(os.path.dirname(output_path), exist_ok=True)
    tts.save(output_path)
    print(f"Audio saved to {output_path}")
except Exception as e:
    print(f"gTTS failed: {e}")
    sys.exit(1)

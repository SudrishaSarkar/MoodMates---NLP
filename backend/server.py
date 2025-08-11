from flask import Flask, request, jsonify
from textblob import TextBlob
import spotipy
from spotipy.oauth2 import SpotifyClientCredentials

app = Flask(__name__)

# Spotify API setup
SPOTIFY_CLIENT_ID = "your_spotify_client_id"
SPOTIFY_CLIENT_SECRET = "your_spotify_client_secret"
sp = spotipy.Spotify(auth_manager=SpotifyClientCredentials(
    client_id=SPOTIFY_CLIENT_ID,
    client_secret=SPOTIFY_CLIENT_SECRET
))

# Emotion to genre mapping
emotion_genre_map = {
    "happy": "pop",
    "joyful": "dance",
    "excited": "party",
    "content": "indie",
    "relaxed": "ambient",
    "stressed": "chill",
    "sad": "acoustic",
    "heartbroken": "piano",
    "angry": "metal",
    "frustrated": "rock",
    "neutral": "indie",
    "surprised": "electronic",
    "curious": "jazz",
    "fearful": "soundtrack",
    "lonely": "folk",
    "hopeful": "singer-songwriter",
    "confused": "instrumental",
    "energetic": "edm",
    "bored": "lo-fi",
    "nostalgic": "classical"
}

# Function to analyze emotion
def analyze_emotion(text):
    blob = TextBlob(text)
    polarity = blob.sentiment.polarity
    if polarity > 0:
        return "positive"
    elif polarity < 0:
        return "negative"
    else:
        return "neutral"

# Function to fetch Spotify songs based on emotion
def get_spotify_playlist_from_api(emotion):
    spotify_genre = emotion_genre_map.get(emotion.lower(), "pop")
    try:
        recommendations = sp.recommendations(seed_genres=[spotify_genre], limit=5)
    except Exception as e:
        return {"error": "Unable to fetch song recommendations. Please try again."}
    
    playlist_name = f"Songs for {emotion.capitalize()} Mood"
    tracks = [
        {
            "title": track["name"],
            "artist": ", ".join([artist["name"] for artist in track["artists"]]),
            "url": track["external_urls"]["spotify"]
        }
        for track in recommendations["tracks"]
    ]
    
    return {
        "playlistName": playlist_name,
        "playlistURL": "https://open.spotify.com/",
        "tracks": tracks
    }

# Endpoint to receive text and process it
@app.route('/process_text', methods=['POST'])
def process_text():
    text = request.json.get('text', '')
    if not text:
        return jsonify({"error": "No text provided"}), 400
    
    emotion = analyze_emotion(text)
    playlist = get_spotify_playlist_from_api(emotion)
    
    advice = ""
    if emotion == "positive":
        advice = "You're doing great! Keep it up!"
    elif emotion == "negative":
        advice = "It's okay to feel down sometimes. Take care of yourself."
    elif emotion == "neutral":
        advice = "Everything seems fine! Keep it balanced."
    
    return jsonify({
        "emotion": emotion,
        "advice": advice,
        "playlist": playlist
    })

if __name__ == '__main__':
    app.run(debug=True, port=5000)

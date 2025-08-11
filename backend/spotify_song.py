import spotipy
from spotipy.oauth2 import SpotifyClientCredentials

def recommend_song(mood):
    sp = spotipy.Spotify(auth_manager=SpotifyClientCredentials(client_id="YOUR_SPOTIFY_CLIENT_ID", client_secret="YOUR_SPOTIFY_CLIENT_SECRET"))
    
    # Define mood-based playlist recommendations
    moods = {
        "positive": "happy",
        "negative": "sad",
        "neutral": "chill"
    }

    mood_playlist = moods.get(mood, "chill")
    results = sp.search(q=mood_playlist, limit=5, type='track')
    
    # Collect song names and URLs
    song_recommendations = []
    for idx, track in enumerate(results['tracks']['items']):
        song_recommendations.append({
            'song': track['name'],
            'artist': track['artists'][0]['name'],
            'url': track['external_urls']['spotify']
        })
    
    return song_recommendations

if __name__ == "__main__":
    print(recommend_song("positive"))

const express = require("express");
const cors = require("cors");
const axios = require("axios"); // Import axios
const app = express();
const port = 5000;

app.use(cors());
app.use(express.json());

const emotionGenreMap = {
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
    "nostalgic": "classical",
};

app.post("/analyze", async (req, res) => {
    const { text } = req.body;

    try {
        // Send the text to the Python server for emotion analysis
        const response = await axios.post("http://localhost:5000/analyze", { text });

        const emotion = response.data.emotion;
        console.log(`Detected Emotion: ${emotion}`);

        // Recommend songs based on the emotion
        const genre = emotionGenreMap[emotion] || "pop";
        const recommendations = await getSpotifyRecommendations(genre);

        res.json({
            emotion,
            playlistURL: recommendations.playlistURL,
            tracks: recommendations.tracks,
        });
    } catch (error) {
        console.error("Error analyzing emotion:", error);
        res.status(500).json({ error: "Error processing the request." });
    }
});

// Function to get Spotify recommendations based on genre (same as in your server.js)
async function getSpotifyRecommendations(genre) {
    // Your code for fetching Spotify recommendations (use your existing logic)
    // This can be from Spotify's API based on the genre
    return {
        playlistURL: "https://open.spotify.com/",
        tracks: [{ title: "Song 1", artist: "Artist 1", url: "#" }],
    };
}

app.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});

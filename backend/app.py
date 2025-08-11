from flask import Flask, request, jsonify
from transcriber import transcribe_speech
from translator import translate_text
from emotion_analyzer import analyze_emotion
from advice_generator import generate_advice
from spotify_song import recommend_song
from flask_cors import CORS  # If you need to handle cross-origin requests

app = Flask(__name__)

# Enable Cross-Origin Resource Sharing (CORS) if necessary for frontend communication
CORS(app)

@app.route('/api/rant', methods=['POST'])
def handle_rant():
    """
    Handle incoming rant audio, process it through transcription, translation,
    emotion analysis, advice generation, and song recommendation.
    """
    try:
        # Retrieve audio file from the POST request
        audio_file = request.files.get('audio')
        language = request.form.get('language', 'en')  # Default to English if language is not provided

        # Ensure audio file is provided
        if not audio_file:
            return jsonify({"error": "No audio file provided."}), 400

        # Step 1: Transcribe speech to text
        text = transcribe_speech(audio_file)
        if not text:
            return jsonify({"error": "Failed to transcribe audio."}), 500

        # Step 2: Translate text if necessary
        translated_text = text  # Default to original text
        if language != 'en':
            translated_text = translate_text(text, target_language='en')
            if not translated_text:
                return jsonify({"error": "Failed to translate text."}), 500

        # Step 3: Analyze the emotion from the text
        emotion = analyze_emotion(translated_text)
        if not emotion:
            return jsonify({"error": "Failed to analyze emotion."}), 500

        # Step 4: Generate advice based on the detected emotion
        advice = generate_advice(emotion)
        if not advice:
            return jsonify({"error": "Failed to generate advice."}), 500

        # Step 5: Recommend a song based on the emotion
        song = recommend_song(emotion)
        if not song:
            return jsonify({"error": "Failed to recommend a song."}), 500

        # Return the processed results
        return jsonify({
            "original_text": text,
            "translated_text": translated_text,
            "emotion": emotion,
            "advice": advice,
            "recommended_song": song
        })

    except Exception as e:
        # Catch all exceptions and return an internal server error
        return jsonify({"error": f"An error occurred: {str(e)}"}), 500


if __name__ == "__main__":
    # Start the Flask server in debug mode
    app.run(debug=True)

from textblob import TextBlob

def analyze_emotion(text):
    # Use TextBlob for sentiment analysis
    blob = TextBlob(text)
    polarity = blob.sentiment.polarity
    subjectivity = blob.sentiment.subjectivity

    # Classifying emotions based on polarity and subjectivity
    if polarity > 0.5:
        return "happy"
    elif 0 < polarity <= 0.5:
        return "joyful"
    elif polarity == 0:
        return "neutral"
    elif -0.5 < polarity < 0:
        return "sad"
    elif polarity <= -0.5:
        return "heartbroken"
    elif polarity < 0 and subjectivity > 0.5:
        return "angry"
    elif polarity < 0 and subjectivity <= 0.5:
        return "frustrated"
    else:
        return "confused"

if __name__ == "__main__":
    example_text = "I am feeling sad and frustrated."
    detected_emotion = analyze_emotion(example_text)
    print(f"Emotion detected: {detected_emotion}")

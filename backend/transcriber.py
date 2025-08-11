import speech_recognition as sr

def transcribe_speech():
    recognizer = sr.Recognizer()

    # Use the microphone to capture audio
    with sr.Microphone() as source:
        print("Listening for your rant...")
        recognizer.adjust_for_ambient_noise(source)
        audio = recognizer.listen(source)
    
    # Convert audio to text using Google Web Speech API
    try:
        print("Transcribing your speech...")
        text = recognizer.recognize_google(audio)
        print(f"Your rant: {text}")
        return text
    except sr.UnknownValueError:
        return "Sorry, I could not understand what you said."
    except sr.RequestError:
        return "There was an issue with the speech recognition service."

if __name__ == "__main__":
    print(transcribe_speech())  # Test the function

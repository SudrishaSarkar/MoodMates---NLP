from googletrans import Translator

def translate_text(text, target_language='en'):
    translator = Translator()
    try:
        translation = translator.translate(text, dest=target_language)
        return translation.text
    except Exception as e:
        return f"Error in translation: {str(e)}"

if __name__ == "__main__":
    original_text = "Hola mundo"  # Test with some text
    print(f"Translated Text: {translate_text(original_text)}")

import random

def generate_advice(emotion):
    advice = {
        "positive": [
            "Keep going, you're doing amazing!",
            "Don't forget to take a break and enjoy the moment!"
        ],
        "negative": [
            "It's okay to feel down, take a deep breath.",
            "Remember, this is temporary, and things will get better."
        ],
        "neutral": [
            "Everything is stable, keep moving forward!",
            "Stay focused and positive, you're on the right track!"
        ]
    }
    
    return random.choice(advice.get(emotion, ["Stay positive!"]))

if __name__ == "__main__":
    print(generate_advice("negative"))

import React, { useState } from "react";
import "./App.css";

function App() {
  // State to control GIF visibility
  const [showGif, setShowGif] = useState(true);
  // State for input value
  const [inputValue, setInputValue] = useState("");
  // State to hold all messages (both user and chatbot)
  const [messages, setMessages] = useState([]);

  // Handler for sending message
  const handleSendClick = () => {
    if (inputValue.trim()) {
      // Add the user's message to the messages array
      setMessages((prevMessages) => [
        ...prevMessages,
        { type: "user", text: inputValue },
      ]);

      // Simulate a chatbot response (You can replace this with a real response)
      setTimeout(() => {
        setMessages((prevMessages) => [
          ...prevMessages,
          { type: "chatbot", text: "I am here to listen!" },
        ]);
      }, 1000); // Add a slight delay for the chatbot response

      // Reset the input box
      setInputValue("");
      setShowGif(false); // Hide the GIF if needed
    }
  };

  // Handle Enter key press in the input box
  const handleKeyPress = (e) => {
    if (e.key === "Enter") {
      handleSendClick(); // Trigger the send action
    }
  };

  return (
    <div className="homepage">
      <header className="header">
        <div
          style={{
            color: "#35522B",
            textAlign: "center",
            fontFeatureSettings: "'liga' off, 'clig' off",
            fontFamily: "Nerko One",
            fontSize: "60px",
            fontStyle: "normal",
            fontWeight: 400,
            lineHeight: "120%",
            letterSpacing: "-1.5px",
            alignItems: "center",
          }}
        >
          MoodMate
        </div>

        <div
          style={{
            alignItems: "center",
            textAlign: "center",
            color: "#35522B",
            fontFeatureSettings: "'liga' off, 'clig' off",
            fontFamily: "Neucha",
            fontSize: "30px",
            fontStyle: "normal",
            fontWeight: 400,
            lineHeight: "120%",
            letterSpacing: "-0.96px",
          }}
        >
          Vent, rant, talk — it's your space, your pace.
        </div>
      </header>

      {/* Conditionally render the GIF */}
      {showGif && (
        <div className="robot-section">
          <img
            src="assets/Robot1.gif"
            alt="MoodMate Robot"
            className="robot-gif"
          />
          <div className="speech-bubble">
            Got no one to talk to? <br /> I am here for you when no one is.
          </div>
        </div>
      )}

      {/* Messages Section */}
      <div className="messages-section">
        {messages.map((message, index) => (
          <div
            key={index}
            className={`message-box ${
              message.type === "user" ? "user-box" : "chatbot-box"
            }`}
          >
            {message.text}
          </div>
        ))}
      </div>

      <div className="input-section">
        <input
          type="text"
          placeholder="Type your feelings away or record..."
          className="input-box"
          value={inputValue} // Controlled input value
          onChange={(e) => setInputValue(e.target.value)} // Update state on input
          onKeyPress={handleKeyPress} // Detect Enter key press
        />

        <button className="send-button" onClick={handleSendClick}>
          ➤
        </button>
      </div>
    </div>
  );
}

export default App;

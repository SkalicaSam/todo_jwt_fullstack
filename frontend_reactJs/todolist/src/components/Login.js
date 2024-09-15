import React, { useState, useContext } from 'react';
import axios from 'axios';
import { AuthContext } from './AuthContext';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const { login } = useContext(AuthContext);  // Používáme login z AuthContextu

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/login', {
        username,
        password,
      });
      login(response.data.token);  // Ukládáme token pomocí login funkce
      alert('Prihlásenie úspešné!');
    } catch (error) {
      console.error('Prihlásenie zlyhalo', error);
      alert('Prihlásenie zlyhalo');
    }
  };

  return (
    <div>
      <h2>Prihlásenie</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Užívateľské meno:</label>
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Heslo:</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit">Prihlásiť sa</button>
      </form>
    </div>
  );
};

export default Login;




      
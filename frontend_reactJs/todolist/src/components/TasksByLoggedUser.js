import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useContext, useEffect, useState } from 'react';
import { AuthContext } from './AuthContext';

const TasksByLoggedUser = () => {
  const { token, logout } = useContext(AuthContext);  // Používáme logout místo setToken
  const [tasks, setTasks] = useState([]);
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);  // Stav pro načítání

  useEffect(() => {
    const fetchTasks = async () => {
      try {
        const response = await axios.get('http://localhost:8080/tasks/usertasks', {
          headers: { Authorization: `Bearer ${token}` },
        });
        setTasks(response.data);
      } catch (error) {
        setError(`Nepodarilo sa načítať úlohy: ${error.message}`);
        console.error("Error fetching tasks:", error.response || error);
      } finally {
        setLoading(false);  // Po dokončení načítání nastavíme loading na false
      }
    };

    if (token) {
      fetchTasks();
    }else {
        setTasks([]); // Vymažeme úkoly, když není token
        setLoading(false); // I když není token, načítání ukončíme
      }
  }, [token]);

  const handleLogout = async () => {  // Přidáme async tady
    try {
      // Pošleme POST požadavek na /logout s tokenem
      await axios.post('http://localhost:8080/logout', {
        headers: { Authorization: `Bearer ${token}` },
      });
      // Odhlásíme uživatele lokálně
        logout(); // Používáme funkci logout z AuthContextu
        setTasks([]);
        navigate('/tasks');
    } catch (error) {
        console.error('Chyba při odhlašování:', error);
      }
  };

  if (loading) {
    // Místo komponenty vrátíme informaci o načítání, dokud není hotové
    return <p>Načítám data...</p>;
  }

  return (
    <div>
      <h2>Vaše úlohy</h2>
      {error && <p>{error}</p>}
      <ul>
        {tasks.map(task => (
          <li key={task.id}>{task.name}</li>
        ))}
      </ul>
      {token ? <p>Váš token: {token}</p> : <p>Není k dispozici žádný token.</p>}
      
       {/* Zobrazí se tlačítko pouze pokud je token přítomen */}
       {token && (
        <button onClick={handleLogout}>Odhlásiť sa</button>
      )}
    </div>
  );
};

export default TasksByLoggedUser;
import 'bootstrap/dist/css/bootstrap.min.css';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { Link } from 'react-router-dom';

const Homepage = () => {

return(
    <>

        <div>
            <h1>Vítejte na mé jednoduché stránce v Reactu! <br></br>
                http://localhost:3000/</h1>
            <p>Toto je moje první stránka vytvořená v ReactJS.</p>
        </div>

    </>
);
};


export default Homepage;
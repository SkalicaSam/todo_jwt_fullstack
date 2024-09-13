
import './App.css';
import { HashRouter as Router, Route, Routes, Navigate} from 'react-router-dom';

import Homepage from './components/Homepage'; 
import TasksByLoggedUser from './components/TasksByLoggedUser'; 

function App() {
  return (
    <> < Router>
          {/* <Navbar/> */}
          <Routes> 
    
            <Route path='/home' exact element={<Homepage/>}></Route>
            <Route path='/' exact element={<Navigate to='/home' />} ></Route>
            <Route path='/tasks' exact Component={TasksByLoggedUser}></Route>
              {/* <Route path='/users' exact Component={User}></Route>
              <Route path='/user/:id' exact Component={UserDetails}></Route>
              <Route path='/users/user/:id/edit' exact Component={UserDetailEdit}></Route>
              <Route path='/users/user/create' exact Component={UserCreate}></Route>
              <Route path='/users/user/:id/usertasks' exact Component={TasksByUserId}></Route>
              <Route path='/tasks/:id' exact Component={TaskDetails}></Route>
              <Route path='/tasks/:id/edit' exact Component={TaskEdit}></Route>
              <Route path='/users/user/:userId/taskCreate' exact Component={TasksCreateByUserId}></Route> */}

          </Routes>
        </Router>
    </>
  );
}

export default App;




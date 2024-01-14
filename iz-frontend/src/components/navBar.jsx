import React from "react";
import { Link, NavLink } from "react-router-dom";
const NavBar = () => {
  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light">
      <Link className="navbar-brand" to="/homePage">
        Home page
      </Link>

      <div className="collapse navbar-collapse" id="navbarNavAltMarkup">
        <div className="navbar-nav">
          <NavLink className="nav-link" to="/components">
            PC Components
          </NavLink>
        </div>
        <div className="navbar-nav">
          <NavLink className="nav-link" to="/computers">
            Desktop Computers
          </NavLink>
        </div>
      </div>
    </nav>
  );
};

export default NavBar;

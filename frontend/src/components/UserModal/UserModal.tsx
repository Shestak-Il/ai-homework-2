import React from "react";
import { User } from "../../types/user";
import styles from "./UserModal.module.css";

interface UserModalProps {
  user: User;
  onClose: () => void;
}

const UserModal: React.FC<UserModalProps> = ({ user, onClose }) => (
  <div className={styles.overlay} onClick={onClose}>
    <div className={styles.modal} onClick={(e) => e.stopPropagation()}>
      <button className={styles.closeBtn} onClick={onClose}>×</button>
      <h2>{user.name}</h2>
      <p><strong>Username:</strong> {user.username}</p>
      <p><strong>Email:</strong> {user.email}</p>
      <p>
        <strong>Address:</strong> {user.address.street}, {user.address.suite}, {user.address.city}, {user.address.zipcode}
      </p>
      <p>
        <strong>Geo:</strong> 
        <a
          href={`https://maps.google.com/?q=${user.address.geo.lat},${user.address.geo.lng}`}
          target="_blank"
          rel="noopener noreferrer"
        >
          {user.address.geo.lat}, {user.address.geo.lng}
        </a>
      </p>
      <p><strong>Phone:</strong> {user.phone}</p>
      <p>
        <strong>Website:</strong> 
        <a href={`http://${user.website}`} target="_blank" rel="noopener noreferrer">
          {user.website}
        </a>
      </p>
      <p><strong>Company:</strong> {user.company.name}</p>
      <p><strong>Catch Phrase:</strong> {user.company.catchPhrase}</p>
      <p><strong>BS:</strong> {user.company.bs}</p>
    </div>
  </div>
);

export default UserModal; 
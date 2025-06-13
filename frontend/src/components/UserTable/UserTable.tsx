import React from "react";
import { User } from "../../types/user";
import styles from "./UserTable.module.css";

interface UserTableProps {
  users: User[];
  onUserClick: (user: User) => void;
  onDelete: (id: number) => void;
}

const UserTable: React.FC<UserTableProps> = ({ users, onUserClick, onDelete }) => (
  <table className={styles.table}>
    <thead>
      <tr>
        <th>Name / Email</th>
        <th>Address</th>
        <th>Phone</th>
        <th>Website</th>
        <th>Company</th>
        <th>Action</th>
      </tr>
    </thead>
    <tbody>
      {users.map((user) => (
        <tr key={user.id} onClick={() => onUserClick(user)} className={styles.row}>
          <td>
            <div>{user.name}</div>
            <div className={styles.email}>{user.email}</div>
          </td>
          <td>
            {user.address.street}, {user.address.city}
          </td>
          <td>{user.phone}</td>
          <td>
            <a href={`http://${user.website}`} target="_blank" rel="noopener noreferrer">
              {user.website}
            </a>
          </td>
          <td>{user.company.name}</td>
          <td>
            <button
              className={styles.deleteBtn}
              onClick={(e) => {
                e.stopPropagation();
                onDelete(user.id);
              }}
            >
              ×
            </button>
          </td>
        </tr>
      ))}
    </tbody>
  </table>
);

export default UserTable; 
import React, { useEffect, useState } from "react";
import { User } from "./types/user";
import UserTable from "./components/UserTable/UserTable";
import UserModal from "./components/UserModal/UserModal";
import styles from "./App.module.css";

const App: React.FC = () => {
  const [users, setUsers] = useState<User[]>([]);
  const [selectedUser, setSelectedUser] = useState<User | null>(null);

  useEffect(() => {
    fetch("https://jsonplaceholder.typicode.com/users")
      .then((res) => res.json())
      .then((data) => setUsers(data));
  }, []);

  const handleDelete = (id: number) => {
    setUsers((prev) => prev.filter((user) => user.id !== id));
  };

  return (
    <div className={styles.container}>
      <h1>User Directory</h1>
      <UserTable
        users={users}
        onUserClick={setSelectedUser}
        onDelete={handleDelete}
      />
      {selectedUser && (
        <UserModal user={selectedUser} onClose={() => setSelectedUser(null)} />
      )}
    </div>
  );
};

export default App;

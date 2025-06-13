# Frontend User Directory Application

A modern, responsive user directory built with React, TypeScript, and CSS Modules. This application fetches user data from the [JSONPlaceholder API](https://jsonplaceholder.typicode.com) and provides a professional interface for viewing and managing users.

## 🚀 Project Overview
- Displays user data in a table-like layout
- View detailed user info in a modal
- Delete users (client-side only)
- Responsive and visually appealing design

## ✨ Features
- Fetches users from JSONPlaceholder `/users` endpoint
- Table with columns: Name/Email, Address, Phone, Website, Company, Action
- Modal with all user details and map link
- Delete user from list (client-side)
- Responsive design for all devices
- Modern CSS with animations and feedback

## 🛠️ Tech Stack
- React
- TypeScript
- CSS Modules
- JSONPlaceholder API

## 📦 Folder Structure
```
frontend/
  src/
    components/
      UserTable/
        UserTable.tsx
        UserTable.module.css
      UserModal/
        UserModal.tsx
        UserModal.module.css
    types/
      user.ts
    App.tsx
    App.module.css
  package.json
  tsconfig.json
  ...
```

## ⚙️ Setup Instructions
1. **Clone the repository:**
   ```bash
   git clone <your-repo-url>
   cd frontend
   ```
2. **Install dependencies:**
   ```bash
   npm install
   ```
3. **Start the development server:**
   ```bash
   npm start
   ```
4. **Open in your browser:**
   Visit [http://localhost:3000](http://localhost:3000)

## 📝 Usage
- Click on a user row to view details in a modal.
- Click the red '×' to delete a user from the list.
- The app is fully responsive and works on all devices.

## 📄 License
This project is open source and available under the [MIT License](LICENSE).

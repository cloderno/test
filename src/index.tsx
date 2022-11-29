import React from 'react';
import ReactDOM from 'react-dom/client';
import Modal from './components/Table/Modal'
import Table from './components/Table/Table';
import './index.css';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <React.StrictMode>
    <Table />
  </React.StrictMode>
);


import React, { FC, useState } from 'react'
import {LocalStorageService} from '../../services/service';

const Modal: FC = () => {
  const service = new LocalStorageService();
  const [data, setData] = useState();

  return (
    <button onClick={() => {
      service.setLocalStorageData();
      const res = service.getLocalStorageData();

      setData(res)
      console.log(data)
    }}>Modal</button>

    
  )
}

export default Modal
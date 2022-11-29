import React, { FC, useState, useEffect } from 'react'
import {LocalStorageService} from '../../services/service';
import { Button, Form, Input, InputNumber, Modal as AntModal, Radio } from 'antd';
import { IModalFormProps, IProduct } from '../../interfaces';

const Modal: FC<IModalFormProps> = ({ open, onCreate, onCancel }) => {
  const service = new LocalStorageService();
  const [form] = Form.useForm();

  const addItem = () => {
    form
      .validateFields()
      .then((values) => {
        let lsData = service.getLocalStorageData();
        let lastId: number = lsData[lsData.length - 1].id;

        const data: IProduct[] = [{
          id: lastId,
          title: values.title,
          description: values.description,
          price: parseInt(values.price),
          stock: parseInt(values.stock)
        }]

        service.addToLocalStorage(data);

        form.resetFields();
        onCreate(values);
      })
      .catch((info) => {
        console.log('Validate Failed:', info);
      });
  }

  return (
  <AntModal
      open={open}
      title="Add new Item"
      okText="Create"
      cancelText="Cancel"
      onCancel={onCancel}
      onOk={() => addItem()}
    >
      <Form
        form={form}
        layout="vertical"
        name="form_in_modal"
        initialValues={{ modifier: 'public' }}
      >
        <Form.Item
          name="title"
          label="Title"
          rules={[{ required: true, message: 'Please input the title of collection!' }]}
        >
          <Input />
        </Form.Item>
        <Form.Item 
          name="description" 
          label="Description"
          rules={[{ required: true, message: 'Please input the description of collection!' }]}>
          <Input type="textarea" />
        </Form.Item>
        <Form.Item 
          name="price" 
          label="Price"
          rules={[{ required: true, message: 'Please input the price of collection!' }]}>
          <InputNumber type="textarea" />
        </Form.Item>
        <Form.Item 
          name="stock" 
          label="Stock"
          rules={[{ required: true, message: 'Please input the stock of collection!' }]}>
          <InputNumber type="textarea" />
        </Form.Item>
      </Form>
    </AntModal>
  )
}

export default Modal
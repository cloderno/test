import React, { FC, useState, useEffect } from 'react'
import {Button, Form, Input, InputNumber, Popconfirm, Table as AntTable, Typography} from 'antd'
import { LocalStorageService } from '../../services/service';
import { IProduct } from '../../interfaces';
import Modal from './Modal'

const EditableCell = ({
  editing,
  dataIndex,
  title,
  inputType,
  record,
  index,
  children,
  ...restProps
}: {editing: boolean, dataIndex: number, title: string, inputType: string, record: string, index: number, children: any}) => {
  const inputNode = inputType === 'number' ? <InputNumber /> : <Input />;
  return (
    <td {...restProps}>
      {editing ? (
        <Form.Item
          name={dataIndex}
          style={{
            margin: 0,
          }}
          rules={[
            {
              required: true,
              message: `Please Input ${title}!`,
            },
          ]}
        >
          {inputNode}
        </Form.Item>
      ) : (
        children
      )}
    </td>
  );
};

const Table: FC = () => {
  const service = new LocalStorageService();
  const [form] = Form.useForm<IProduct>();
  const [data, setData] = useState();
  const [editingid, setEditingid] = useState<number>();
  const [open, setOpen] = useState(false);
  const isEditing = (record: { id: number; }) => record.id === editingid;

  // Первый запуск, добавляем записи в хранилище и обновляем стейт
  useEffect(() => {
    service.setLocalStorageData();

    setData(service.getLocalStorageData());
  }, [])

  // Каждое изменение стейта
  useEffect(() => {
    setData(service.getLocalStorageData());
  }, [data])

  const edit = (record: { id: number; }) => {
    form.setFieldsValue({
      title: '',
      description: '',
      price: undefined,
      stock: undefined,
      ...record,
    });
    setEditingid(record.id);
  };

  const cancel = () => {
    setEditingid(undefined);
  };

  const remove = (record: { id: number; }) => {
    service.removeFromLocalStorage(record.id);
  }

  const save = () => {
    const currentObject: IProduct[] = [{
      id: editingid!,
      title: form.getFieldValue('title'),
      description: form.getFieldValue('description'),
      price: parseInt(form.getFieldValue('price')),
      stock: parseInt(form.getFieldValue('stock'))
    }]

    service.updateLocalStorageData(currentObject);
    service.getLocalStorageData()
    setEditingid(undefined)
  };

  const onCreate = () => {
    setOpen(false);
  };

  const columns = [
    {
      title: 'id',
      dataIndex: 'id',
      width: '5%',
      editable: false,
    },
    {
      title: 'title',
      dataIndex: 'title',
      width: '20%',
      editable: true,
    },
    {
      title: 'description',
      dataIndex: 'description',
      width: '40%',
      editable: true,
    },
    {
      title: 'price',
      dataIndex: 'price',
      width: '20%',
      editable: true,
    },
    {
      title: 'stock',
      dataIndex: 'stock',
      width: '20%',
      editable: true,
    },
    {
      title: 'operation',
      dataIndex: 'operation',
      render: (_: any, record: { id: number; }) => {
        const editable = isEditing(record);
        return editable ? (
          <span>
            <Typography.Link
              onClick={() => save()}
              style={{
                marginRight: 8,
              }}
            >
              Save
            </Typography.Link>
            <Popconfirm title="Sure to cancel?" onConfirm={cancel}>
              <a>Cancel</a>
            </Popconfirm>
          </span>
        ) : (
          <>
            <Typography.Link disabled={editingid !== undefined} onClick={() => edit(record)}>
              Edit
            </Typography.Link>
            <br></br>
            <Typography.Link onClick={() => remove(record)}>
              Remove
            </Typography.Link>
          </>
        );

      },
    },
  ];
  const mergedColumns = columns.map((col) => {
    if (!col.editable) {
      return col;
    }
    return {
      ...col,
      onCell: (record: { id: number; }) => ({
        record,
        inputType: col.dataIndex,
        dataIndex: col.dataIndex,
        title: col.title,
        editing: isEditing(record),
      }),
    };
  });

  return (
    <>
      <Form form={form} component={false}>
        {
          data ? 
          <AntTable
            components={{
              body: {
                cell: EditableCell,
              },
            }}
            bordered
            dataSource={data}
            columns={mergedColumns}
            rowClassName="editable-row"
            pagination={{
              onChange: cancel,
            }}
          />
          :
          <p> loading.. </p>
        }
      </Form>
      <Button
        type="primary"
        onClick={() => {
          setOpen(true);
        }}
      >
        New Collection
      </Button>
      <Modal
            open={open}
            onCreate={onCreate}
            onCancel={() => {
              setOpen(false);
      }}/>
    </>
  )
}

export default Table
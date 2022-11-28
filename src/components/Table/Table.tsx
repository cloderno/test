import React, { FC, useState, useEffect } from 'react'
import {Form, Input, InputNumber, Popconfirm, Table as AntTable, Typography} from 'antd'
import { LocalStorageService } from '../../services/service';

const service = new LocalStorageService();

service.setLocalStorageData();

let originData: any[] | (() => any[]) = service.getLocalStorageData();

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
  const [form] = Form.useForm();
  const [data, setData] = useState(originData);
  const [editingid, setEditingid] = useState('');
  const isEditing = (record: { id: string; }) => record.id === editingid;

  useEffect(() => {
    service.updateLocalStorageData(data);
  }, [data])

  const edit = (record: { id: React.SetStateAction<string>; }) => {
    form.setFieldsValue({
      title: '',
      description: '',
      price: null,
      ...record,
    });
    setEditingid(record.id);
  };
  const cancel = () => {
    setEditingid('');
  };

  const save = async (id: any) => {
    try {
      const row = await form.validateFields();
      const newData = [...data];
      const index = newData.findIndex((item) => id === item.id);
      if (index > -1) {
        const item = newData[index];
        newData.splice(index, 1, {
          ...item,
          ...row,
        });
        setData(newData);
        setEditingid('');
      } else {
        newData.push(row);
        setData(newData);
        setEditingid('');
      }
    } catch (errInfo) {
      console.log('Validate Failed:', errInfo);
    }
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
      title: 'operation',
      dataIndex: 'operation',
      render: (_: any, record: { id: any; }) => {
        const editable = isEditing(record);
        return editable ? (
          <span>
            <Typography.Link
              onClick={() => save(record.id)}
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
          <Typography.Link disabled={editingid !== ''} onClick={() => edit(record)}>
            Edit
          </Typography.Link>
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
      onCell: (record: { id: string; }) => ({
        record,
        inputType: col.dataIndex,
        dataIndex: col.dataIndex,
        title: col.title,
        editing: isEditing(record),
      }),
    };
  });

  return (
    <Form form={form} component={false}>
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
    </Form>
  )
}

export default Table
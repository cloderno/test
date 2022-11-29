export interface IProduct {
    id: number
    title?: string
    description?: string
    price?: number
    stock?: number
}

export interface IValues {
    title: string;
    description: string;
    modifier: string;
  }
  
export interface IModalFormProps {
    open: boolean;
    onCreate: (values: IValues) => void;
    onCancel: () => void;
}
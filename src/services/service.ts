import {IProduct} from '../interfaces'

export class TodoService {
    private baseUrl: string;

    constructor() {
        this.baseUrl = 'https://dummyjson.com/products';
    }

    // Получаем данные из API
    async getTodos(): Promise<IProduct[]> {
        const response = await fetch(this.baseUrl);
        const json = await response.json();

        return json.products;
    }
}

export class LocalStorageService {
    protected service = new TodoService();

    async fetchData() {
        return await this.service.getTodos();
    }

    //  Заполнение пустого хранилища из API
    async setLocalStorageData() {
        if ("Data" in localStorage) {
            JSON.parse(localStorage.getItem('Data') || '{}');
        }
        else {
            localStorage.setItem('Data', JSON.stringify( await this.fetchData()));
        }
    }

    // Отправка данных из хранилища
    getLocalStorageData() {
        return JSON.parse(localStorage.getItem('Data') || '{}');
    }

    // Изменяет уже существующий объект передаваемым
    async updateLocalStorageData(newData: IProduct[]) {
        const data = this.getLocalStorageData();

        let idx = data.findIndex((i: { id: number; }) => i.id === newData[0].id);

        Object.assign(data[idx], {
            title: newData[0].title,
            price: newData[0].price,
            description: newData[0].description,
            stock: newData[0].stock,
        });

        localStorage.setItem('Data', JSON.stringify(data));
    }

    // Удаляет из хранилища по айдишнику
    removeFromLocalStorage(_id: number) {
        const data = this.getLocalStorageData();
        const updatedData = data?.filter((i: { id: number; }) => i.id != _id)

        localStorage.setItem('Data', JSON.stringify(updatedData));
    }

    // Добавляет объект в хранилище
    addToLocalStorage(newData: IProduct[]) {
        const data = this.getLocalStorageData();
        const updatedData = [...data, newData[0]];
        
        localStorage.setItem('Data', JSON.stringify(updatedData));
    }
}

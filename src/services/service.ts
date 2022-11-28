import {IProduct} from '../interfaces'

export class TodoService {
    private baseUrl: string

    constructor() {
        this.baseUrl = 'https://dummyjson.com/products'
    }

    async getTodos(): Promise<IProduct[]> {
        const response = await fetch(this.baseUrl);
        const json = await response.json();

        return json;
    }
}

export class LocalStorageService {
    async setLocalStorageData() {
        const service = new TodoService()
        const data = await service.getTodos()

        localStorage.setItem('Data', JSON.stringify(data))
    }

    getLocalStorageData() {
        return JSON.parse(localStorage.getItem('Data') || '{}').products
    }

    updateLocalStorageData(newData: IProduct[]) {
        localStorage.setItem('Data', JSON.stringify(newData))
    }

    removeFromLocalStorage() {

    }
}

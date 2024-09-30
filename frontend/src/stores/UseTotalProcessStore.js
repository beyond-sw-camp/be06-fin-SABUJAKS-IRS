import { defineStore } from 'pinia';
import axios from 'axios';
import { backend } from '@/const';

export const UseTotalProcessStore = defineStore('totalProcess', {
    state: () => ({}),
    persist: { storage: sessionStorage, },
    actions: {
        async create(requestBody) {
            try {
                const response = await axios.post(
                    `${backend}/total-process/create`, requestBody,
                    { 
                        headers: { 'Content-Type': 'application/json', }, 
                        withCredentials: true
                    }
                );
                return response.data
            } catch (error) {
                return error.response.data
            }
        },
    }
})
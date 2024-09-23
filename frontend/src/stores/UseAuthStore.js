import { defineStore } from 'pinia';
import axios from 'axios';
import { backend } from '@/const';


export const UseAuthStore = defineStore('auth', {
    state: () => ({
        userInfo: {
            email: '',
            name: '',
            role: '',
        },
        isLoggedIn: false,
     }),
    persist: { storage: sessionStorage, },
    actions: {
        async login(requestBody) {
            try {
                const response = await axios.post(
                    `${backend}/auth/login`, 
                    requestBody,
                    { headers: { 'Content-Type': 'application/json', },}
                );
                if(response) {
                    this.isLoggedIn = true;
                    return true
                }
            } catch (error) {
                this.isLoggedIn = false;
                return false
            }
        },
        async logout(){
            try {
                const response = await axios.get(
                    `${backend}/auth/logout`,
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
        async getAuthorities(){
            try {
                const response = await axios.get(
                    `${backend}/test/ex02`,
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
        async getUserInfo() {
            try {
                const response = await axios.get(
                    `${backend}/auth/user-info`,
                    { 
                        headers: { 'Content-Type': 'application/json', },
                        withCredentials: true
                    }
                );
                const { email, name, role } = response.data.result;
                this.userInfo = { email, name, role };
                return response.data
            } catch (error) {
                return error.response.data
            }
        },
    },
});
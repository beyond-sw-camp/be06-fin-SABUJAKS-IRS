import { defineStore } from 'pinia';
import axios from 'axios';
import { backend } from '@/const';


export const UseAuthStore = defineStore('auth', {
    state: () => ({
        userInfo: {
            email: '',
            name: '',
            role: '',
        }
     }),
    persist: { storage: sessionStorage, },
    actions: {
        async login(loginReq) {
            try {
                const response = await axios.post(
                    `${backend}/auth/login`, 
                    loginReq ,
                    { headers: { 'Content-Type': 'application/json', },}
                );
                return response.status
            } catch (error) {
                console.error("로그인에 실패했습니다.")
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
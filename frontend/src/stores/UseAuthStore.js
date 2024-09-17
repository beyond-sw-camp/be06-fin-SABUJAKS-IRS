import { defineStore } from 'pinia';
import axios from 'axios';
import { backend } from '@/const';


export const UseAuthStore = defineStore('auth', {
    state: () => ({
        email: '',
        name: '',
        role: '',
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
                return response.data
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
                console.error("권한을 불러오는데 실패했습니다.")
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
                console.error("권한을 불러오는데 실패했습니다.")
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
                this.email = response.data.result.email
                this.name = response.data.result.name
                this.role = response.data.result.role
                return response.data
            } catch (error) {
                console.error("유저 정보를 불러오는데 실패했습니다.")
            }
        },
    },
});
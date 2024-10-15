import { defineStore } from 'pinia';
import axios from 'axios';
import { backend } from '@/const';


export const UseAuthStore = defineStore('auth', {
    state: () => ({
        userInfo: {
            email: '',
            name: '',
            role: '',
            nickName: ''
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
                    this.getUserInfo(); // 추가
                    if(this.userInfo.name != null) {
                        return true
                    }
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
                this.isLoggedIn = false
                this.userInfo.email = '';
                this.userInfo.name = '';
                this.userInfo.role = '';
                this.userInfo.nickName = '';
                return response.data
            } catch (error) {
                return error.response.data
            }
        },
        async signup(formData) {
            try {
                const response = await axios.post(
                    `${backend}/auth/signup`, formData,
                    {
                        headers: { 'Content-Type': 'multipart/form-data' },
                    }
                );
                return response.data;
            } catch (error) {
                return error.response.data;
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
                const { email, name, role, nickName } = response.data.result;
                this.userInfo = { email, name, role, nickName };
                return response.data
            } catch (error) {
                return error.response.data
            }
        },
        async readSeeker() {
            try {
                const response = await axios.get(
                    `${backend}/auth/seeker/read`,
                    { 
                        headers: { 'Content-Type': 'application/json', },
                        withCredentials: true
                    }
                );
                return response.data;
            } catch (error) {
                return error.response.data;
            }
        },
        async companyVerify(requestBody) {
            try {
                const response = await axios.post(
                    `${backend}/auth/company-verify`, requestBody,
                    { 
                        headers: { 'Content-Type': 'application/json', },
                        withCredentials: true
                    }
                );
                return response.data;
            } catch (error) {
                return error.response.data;
            }
        }
    },
});
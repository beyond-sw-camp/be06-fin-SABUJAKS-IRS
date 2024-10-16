import { defineStore } from 'pinia';
import axios from 'axios';
import { backend } from '@/const';
import {useToast} from "vue-toastification";

export const UseTotalProcessStore = defineStore('totalProcess', {
    state: () => ({
        totalProcessList: [],
        totalProcessListPage: {},
    }),
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

        async readAllTotalProcess(router, announcementIdx, page, size) {
            const toast = useToast();
            try{
                const response = await axios.get(`${backend}/total-process/recruiter/read-all?announcementIdx=${announcementIdx}&page=${page}&size=${size}`, {
                    headers: { 'Content-Type': 'application/json' },
                    withCredentials: true
                });
                this.totalProcessList = response.data.result.content;
                this.totalProcessListPage = response.data.result;
                console.log(this.totalProcessList);
            } catch (error) {
                toast.error(error.response.data.message);
                router.push('/recruiter/total-process');
            }
        }
    }
})
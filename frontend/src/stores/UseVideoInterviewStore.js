import { defineStore } from 'pinia';
import axios from 'axios';
import { backend } from '@/const';


export const UseVideoInterviewStore = defineStore('videoInterivew', {
    state: () => ({}),
    persist: { storage: sessionStorage, },
    actions: {
        async create(requestbody) {
            try {
                const response = await axios.post(
                    `${backend}/video-interview/create`, requestbody,
                    {
                        headers: { 'Content-Type': 'application/json', },
                    }
                );
                return response.data
            } catch (error) {
                return error.response.data
            }
        },
        async readAll(announcementUUID) {
            try {
                const response = await axios.get(
                    `${backend}/video-interview/read-all?announcementUUID=${announcementUUID}`,
                    {
                        headers: { 'Content-Type': 'application/json', },
                    }
                );
                return response.data
            } catch (error) {
                return error.response.data
            }
        },
        async getSessionToken(requestBody) {
            try {
                const response = await axios.post(
                    `${backend}/video-interview/get-session-token`, requestBody,
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
    },
});
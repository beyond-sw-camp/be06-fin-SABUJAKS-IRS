import { defineStore } from 'pinia';
import axios from 'axios';
import { backend } from '@/const';


export const UseVideoInterviewStore = defineStore('VideoInterview', {
    state: () => ({}),
    persist: { storage: sessionStorage, },
    actions: {
        async create(requestbody) {
            try {
                const response = await axios.post(
                    `${backend}/video-interview/create`, requestbody ,
                    { headers: { 'Content-Type': 'application/json', },});
                return response.data
            } catch (error) {
                return error.response.data
            }
        },
        async searchAll(announceUUID) {
            try {
                const response = await axios.get(
                `${backend}/video-interview/search-all?announceUUID=${announceUUID}`,
                { headers: { 'Content-Type': 'application/json', },});
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
                });
                console.log(response)
                return response.data
            } catch (error) {
                return error.response.data
            }
        },
    },
});
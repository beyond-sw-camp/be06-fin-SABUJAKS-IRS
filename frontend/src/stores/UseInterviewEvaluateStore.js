import { defineStore } from 'pinia';
import axios from 'axios';
import { backend } from '@/const';


export const UseInterviewEvaluateStore = defineStore('interviewEvaluate', {
    state: () => ({}),
    persist: { storage: sessionStorage, },
    actions: {
        async createForm(requestBody) {
            try {
                const response = await axios.post(
                    `${backend}/interview-evaluate/create-form`, requestBody,
                    { 
                        headers: { 'Content-Type': 'application/json', }, 
                    }
                );
                return response.data
            } catch (error) {
                return error.response.data
            }
        },
        async searchForm(announcementUUID, interviewScheduleUUID) {
            try {
                const response = await axios.get(
                    `${backend}/interview-evaluate/search-form?announcementUUID=${announcementUUID}&interviewScheduleUUID=${interviewScheduleUUID}`,
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
        async readAllResumeInfo(announcementUUID, interviewScheduleUUID) {
            try {
                const response = await axios.get(
                    `${backend}/interview-evaluate/read-all/resume-info?announcementUUID=${announcementUUID}&interviewScheduleUUID=${interviewScheduleUUID}`,
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
        async createEvaluate(requestBody) {
            try {
                const response = await axios.post(
                    `${backend}/interview-evaluate/create-evaluate`, requestBody,
                    {
                        headers: { 'Content-Type': 'application/json', },
                        withCredentials: true
                    }
                );
                return response.data
            } catch (error) {
                return error.response.data
            }
        }
    },
});
import { defineStore } from 'pinia';
import axios from 'axios';
import { backend } from '@/const';


export const UseInterviewEvaluateStore = defineStore('interviewEvaluate', {
    state: () => ({
     }),
    persist: { storage: sessionStorage, },
    actions: {
        async createForm(requestBody) {
            try {
                const response = await axios.post(
                    `${backend}/interview-evaluate/create-form`,
                    requestBody ,
                    { headers: { 'Content-Type': 'application/json', },}
                );
                return response.data
            } catch (error) {
                console.error("면접폼 생성에 실패했습니다.")
            }
        },
        async searchFormforEstimator(announcementUUID, interviewScheduleUUID) {
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
                console.error("면접폼 조회에 실패했습니다.")
            }
        },
    },
});
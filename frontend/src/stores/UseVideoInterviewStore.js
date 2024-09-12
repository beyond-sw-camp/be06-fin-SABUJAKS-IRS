import { defineStore } from 'pinia';
import axios from 'axios';
import { backend } from '@/const';


export const UseVideoInterviewStore = defineStore('VideoInterview', {
    state: () => ({
        room: [],
    }),
    persist: { storage: sessionStorage, },
    actions: {
        async createVideoInterview(videoInterviewCreateReq) {
            try {
                const response = await axios.post(
                    `${backend}/video-interview/create`,
                    videoInterviewCreateReq ,
                    { headers: { 'Content-Type': 'application/json', },});
                return response.data
            } catch (error) {
                console.error("화상 면접 방 생성에 실패했습니다.")
            }
        },
        async searchAllVideoInterview(announceUUID) {
            try {
                const response = await axios.get(
                    `${backend}/video-interview/search-all?announceUUID=${announceUUID}`,
                    { headers: { 'Content-Type': 'application/json', },});
                return response.data
            } catch (error) {
                console.error("화상 면접 방 목록조회에 실패했습니다.")
            }
        },
        async getVideoInterviewToken(videoInterviewJoinReq) {
            try {
                const response = await axios.post(
                    `${backend}/video-interview/token`,
                    videoInterviewJoinReq,
                    {headers: { 'Content-Type': 'application/json', },
                });
                return response.data; // 토큰담김
            } catch (error) {
                console.error("화상 면접 방 참여에 실패했습니다.")
            }
        },
    },
});
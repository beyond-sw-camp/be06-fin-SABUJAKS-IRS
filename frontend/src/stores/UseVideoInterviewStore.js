import { defineStore } from 'pinia';
import axios from 'axios';
import { backend } from '@/const';


export const UseVideoInterviewStore = defineStore('VideoInterview', {
    state: () => ({
        room: [],
    }),
    persist: { storage: sessionStorage, },
    actions: {
        async createVideoInterviewRoom(videoInterviewCreateRoomReq) {
            try {
                const response = await axios.post(`${backend}/video-interview/room/create`,
                    videoInterviewCreateRoomReq ,
                    { headers: { 'Content-Type': 'application/json', },});
                return response.data
            } catch (error) {
                console.error("방 생성에 실패했습니다.")
            }
        },
    },
});
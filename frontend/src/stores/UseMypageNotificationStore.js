import { defineStore } from "pinia";
import axios from "axios";
import {backend} from "@/const";

// 전역 저장소 생성
export const UseMypageNotificationStore = defineStore('notification', {
    state: () => (
        {alarmList: [{ idx: 0, createdAt: "", time: "", section: "", floor: 0}]}
    ),
    actions: {
        // 면접 일정 전체 불러오기
        async readAllAlarm() {
            try{
                const response = await axios.get(
                    // `api/interview-schedule/create`,
                    `${backend}/alarm/read-all`,
                    // 쿠키 포함
                    // { withCredentials: true }
                );

                console.log(response.data.result);

                return response.data.result;
            } catch (error) {
                console.error("Error: ", error);

                return false;
            }
        },

        // 알림 읽음 표시
        async updateStatus(idx) {
            try{
                const response = await axios.get(
                    // `api/interview-schedule/create`,
                    `${backend}/alarm/update-status/${idx}`,
                    // 쿠키 포함
                    // { withCredentials: true }
                );

                console.log(response.data.result);

                return response.data.result;
            } catch (error) {
                console.error("Error: ", error);

                return false;
            }
        },

        // async getReservationDetail() {
        //     const response = await axios.get(
        //         `api/reservation/reservation-list?`,{ // 쿠키 포함
        //             withCredentials: true
        //         }
        //     );
        //
        //     console.log(response);
        //
        //     this.reservationList = response.data.result;
        // },

        // async getTimeList(floor, section) {
        //     const response = await axios.get(
        //         `api/reservation/time-list?floor=${floor}&section=${section}`,
        //     );
        //
        //     console.log(response);
        //
        //     this.reservationTimeList = response.data.result;
        //
        //     return response.data.result;
        // },
    }
});
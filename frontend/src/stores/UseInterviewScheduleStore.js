import { defineStore } from "pinia";
import axios from "axios";

// 전역 저장소 생성
export const UseInterviewScheduleStore = defineStore('reservation', {
    state: () => (
        {reservationList: [{ idx: 0, createdAt: "", time: "", section: "", floor: 0}]},
            {reservationTimeList: [[{ idx: 0 }], [{idx: 0,}]]}
    ),
    actions: {
        async createInterviewSchedule(interviewData) {
            if (interviewData.isOnline === "대면") {
                interviewData.isOnline = false;
            } else if (interviewData.isOnline === "온라인") {
                interviewData.isOnline = true;
            }

            console.log(interviewData.seekerList);
            console.log(interviewData.interviewerList);
            console.log(interviewData.isOnline);
            console.log(interviewData.interviewDate);
            console.log(interviewData.interviewStart);
            console.log(interviewData.interviewEnd);
            try{
                const response = await axios.post(
                    // `api/interview-schedule/create`,
                    `/api/api/interview-schedule/create`,
                    interviewData,
                    // 쿠키 포함
                    // { withCredentials: true }
                );

                console.log(response);

                return true;
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
import { defineStore } from "pinia";
import axios from "axios";
import {backend} from "@/const";

// 전역 저장소 생성
export const UseInterviewScheduleStore = defineStore('reservation', {
    state: () => (
        {reservationList: [{ idx: 0, createdAt: "", time: "", section: "", floor: 0}]},
            {reservationTimeList: [[{ idx: 0 }], [{idx: 0,}]]}
    ),
    actions: {
        // 면접 일정 생성
        async createInterviewSchedule(interviewData) {
            if (interviewData.isOnline === "대면") {
                interviewData.isOnline = false;
            } else if (interviewData.isOnline === "온라인") {
                interviewData.isOnline = true;
            }

            console.log(interviewData.announcementIdx);
            try{
                const response = await axios.post(
                    // `api/interview-schedule/create`,
                    `${backend}/interview-schedule/create`,
                    interviewData,
                    // 쿠키 포함
                    { withCredentials: true }
                );

                console.log(response);

                return response.data.success;
            } catch (error) {
                console.error("Error: ", error);

                return false;
            }
        },

        // 면접 일정 전체 불러오기
        async readAllAnnouncement(careerBase, pageNum) {
            pageNum = Number(pageNum)-1;
            try{
                const response = await axios.get(
                    // `api/interview-schedule/create`,
                    `${backend}/announcement/recruiter/read-all?` +
                    `careerBase=${careerBase}` +
                    `&pageNum=${pageNum}`,
                    // 쿠키 포함
                    { withCredentials: true }
                );

                console.log(response)

                return response.data.result;
            } catch (error) {
                console.error("Error: ", error);

                return false;
            }
        },

        // 면접 일정 전체 불러오기
        async getTotalAnnouncement(careerBase) {
            try{
                const response = await axios.get(
                    // `api/interview-schedule/create`,
                    `${backend}/announcement/recruiter/read-all/count?careerBase=${careerBase}`,
                    // 쿠키 포함
                    { withCredentials: true }
                );

                return response.data.result;
            } catch (error) {
                console.error("Error: ", error);

                return false;
            }
        },

        // 면접 일정 전체 불러오기
        async readAllInterviewSchedule(reqData, pageNum) {
            pageNum = Number(pageNum)-1;
            try{
                const response = await axios.get(
                    // `api/interview-schedule/create`,
                    `${backend}/interview-schedule/read-all?` +
                    `careerBase=${reqData.careerBase}` +
                    `&announcementIdx=${reqData.announcementIdx}` +
                    `&pageNum=${pageNum}`,
                    // 쿠키 포함
                    { withCredentials: true }
                );

                console.log(response.data.result);

                return response.data.result;
            } catch (error) {
                console.error("Error: ", error);

                return false;
            }
        },

        async getTotalInterviewSchedule(reqData) {
            try{
                const response = await axios.get(
                    // `api/interview-schedule/create`,
                    `${backend}/interview-schedule/read-all/count?` +
                    `careerBase=${reqData.careerBase}` +
                    `&announcementIdx=${reqData.announcementIdx}`,
                    // 쿠키 포함
                    { withCredentials: true }
                );

                return response.data.result;
            } catch (error) {
                console.error("Error: ", error);

                return false;
            }
        },

        async getAvailableTimes(requestData) {
            try{
                const response = await axios.post(
                    `${backend}/interview-schedule/available-times`,
                    requestData
                );

                console.log(response.data.result);

                return response.data.result;
            } catch (error) {
                console.error("Error: ", error);
                return false;
            }
        },

        // 일정 조율 생성
        async createReSchedule(scheduleData) {
            console.log(scheduleData);
            try{
                const response = await axios.post(
                    `${backend}/interview-schedule/create/re-schedule`,
                    scheduleData
                );

                console.log(response.data.result);

                return response.data.result;
            } catch (error) {
                console.error("Error: ", error);
                return false;
            }
        },

        //리스트업
        async readAllReSchedule(announceIdx, pageNum) {
            try{
                const reScheduleResponse = await axios.get(
                    `${backend}/interview-schedule/read-all/re-schedule?` +
                    `announcementIdx=${announceIdx.announcementIdx}` +
                    `&pageNum=${pageNum}` ,
                    {withCredentials: true}
                );

                console.log(reScheduleResponse.data.result);

                return reScheduleResponse.data.result;
            } catch (error) {
                console.error("Error: ", error);
                return false;
            }
        },

        async getTotalReSchedule(reqData) {
            try{
                const response = await axios.get(
                    // `api/interview-schedule/create`,
                    `${backend}/interview-schedule/read-all/count/re-schedule?` +
                    `&announcementIdx=${reqData.announcementIdx}`,
                    // 쿠키 포함
                    { withCredentials: true }
                );

                console.log(response);

                return response.data.result;
            } catch (error) {
                console.error("Error: ", error);

                return false;
            }
        },

        // 상세정보
        async readInterviewSchedule(interviewScheduleIdx) {
            try{
                const reScheduleResponse = await axios.get(
                    `${backend}/interview-schedule/read?interviewScheduleIdx=${interviewScheduleIdx}`,
                    {withCredentials: true}
                );

                console.log(reScheduleResponse.data.result);

                return reScheduleResponse.data.result;
            } catch (error) {
                console.error("Error: ", error);
                return false;
            }
        },

        async updateInterviewSchedule(updateData) {
            try{
                const response = await axios.post(
                    `${backend}/interview-schedule/update`,
                    updateData,
                    {withCredentials: true}
                );

                return response.data.success;
            } catch (error) {
                console.error("Error: ", error);
                return false;
            }
        },
    }
});
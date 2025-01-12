import { defineStore } from "pinia";
import axios from "axios";
import {backend} from "@/const";
import {useToast} from "vue-toastification";

// 전역 저장소 생성
export const UseInterviewScheduleStore = defineStore('reservation', {
    state: () => ({
            announcementIdx: null,
            announcementUuid: "",
            careerBase: "",
            interviewNum: 0,
            currentInterviewNum: 0,
            scheduleInfo: {},

            interviewList: [],
            interviewListPage: {},
        }
    ),
    actions: {
        setAnnouncementIdx(announcementIdx) {
            this.announcementIdx = announcementIdx;
        },
        setAnnouncementUuid(announcementUuid) {
            this.announcementUuid = announcementUuid;
        },
        setCareerBase(careerBase) {
            this.careerBase = careerBase;
        },
        setScheduleInfo(scheduleInfo) {
            this.scheduleInfo = scheduleInfo;
        },
        setInterviewNum(interviewNum) {
            this.interviewNum = interviewNum;
        },

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
                    { headers: { 'Content-Type': 'application/json', },
                        withCredentials: true}
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
        async readAllInterviewSchedule(router, reqData, pageNum) {
            const toast = useToast();
            try{
                const response = await axios.get(
                    // `api/interview-schedule/create`,
                    `${backend}/interview-schedule/read-all?` +
                    `careerBase=${reqData.careerBase}` +
                    `&announcementIdx=${reqData.announcementIdx}` +
                    `&interviewNum=${reqData.currentInterviewNum}` +
                    `&pageNum=${pageNum}`,
                    // 쿠키 포함
                    { withCredentials: true }
                );

                this.interviewList = response.data.result.content;
                this.interviewListPage = response.data.result;

                return response.data.result;
            } catch (error) {
                toast.error("인터뷰 조회에 실패했습니다.");
            }
        },

        // async getTotalInterviewSchedule(reqData) {
        //     try{
        //         const response = await axios.get(
        //             // `api/interview-schedule/create`,
        //             `${backend}/interview-schedule/read-all/count?` +
        //             `careerBase=${reqData.careerBase}` +
        //             `&announcementIdx=${reqData.announcementIdx}`,
        //             // 쿠키 포함
        //             { withCredentials: true }
        //         );
        //         this.totalPages = response.data.result.content;
        //
        //         return response.data.result;
        //     } catch (error) {
        //         console.error("Error: ", error);
        //
        //         return false;
        //     }
        // },

        async createVideoInterview(uuidData) {
            try{
                const response = await axios.post(
                    `${backend}/video-interview/create`,
                    uuidData
                );

                console.log(response.data.result);

                return response.data.result;
            } catch (error) {
                console.error("Error: ", error);
                return false;
            }
        },

        async createAllVideoInterview(announcementUuid, announcementIdx){
            try{
                const response = await axios.post(
                    `${backend}/video-interview/create-all`,
                    {
                        announcementUuid: announcementUuid,
                        announcementIdx: announcementIdx
                    }
                );

                console.log(response);

                return response;
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
                    scheduleData,
                    {withCredentials: true}
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
            pageNum = Number(pageNum)-1;
            try{
                const reScheduleResponse = await axios.get(
                    `${backend}/interview-schedule/read-all/re-schedule?` +
                    `announcementIdx=${announceIdx}` +
                    `&pageNum=${pageNum}` ,
                    {withCredentials: true}
                );

                return reScheduleResponse.data.result;
            } catch (error) {
                console.error("Error: ", error);
                return false;
            }
        },

        async getTotalReSchedule(announcementIdx) {
            try{
                const response = await axios.get(
                    // `api/interview-schedule/create`,
                    `${backend}/interview-schedule/read-all/count/re-schedule?` +
                    `&announcementIdx=${announcementIdx}`,
                    // 쿠키 포함
                    { withCredentials: true }
                );

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

        async getSeeker(announcementIdx, currentInterviewNum) {
            try{
                const response = await axios.get(
                    `${backend}/interview-schedule/read-all/seeker?announcementIdx=${announcementIdx}&interviewNum=${currentInterviewNum}`,
                    {withCredentials: true}
                );

                console.log(response);

                return response.data.result;
            } catch (error) {
                console.error("Error: ", error);
                return false;
            }
        },

        // 시간정보
        async getAvailableTime(newDate, newTeam, announcementIdx) {
            console.log(newDate, newTeam, announcementIdx);
            try {
                const response = await axios.get(
                    `${backend}/interview-schedule/available-times?` +
                    `interviewDate=${newDate}&teamIdx=${newTeam}&announcementIdx=${announcementIdx}`,
                    {withCredentials: true}
                )

                console.log("시간정보: ", response.data);

                return response.data.result;
            } catch (error) {
                console.log("시간 가져오기 실패: ", error);
            }
        }
    }
});
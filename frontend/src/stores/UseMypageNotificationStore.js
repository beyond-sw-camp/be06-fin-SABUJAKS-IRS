import { defineStore } from "pinia";
import axios from "axios";
import {backend} from "@/const";
import { UseAuthStore } from '@/stores/UseAuthStore';

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
                    { withCredentials: true }
                );

                console.log(response.data.result);

                return response.data.result;
            } catch (error) {
                // console.error("Error: ", error);
                // return false;

                if (error.response.status == 401) {
                    console.log("여긴알람스토어 리드올알람");
                    // toast.error(error.response.data.message);
                    console.log(error.response.data.message);
                    // toast.error("세션이 만료되었습니다. 다시 로그인 해 주세요.");
                    const authStore = UseAuthStore();
                    authStore.logout();
                } else {
                    return error.response.data;
                }
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

    }
});
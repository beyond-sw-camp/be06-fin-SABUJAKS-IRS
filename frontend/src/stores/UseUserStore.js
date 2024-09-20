import { defineStore } from "pinia"
import axios from "axios";
import {backend} from "@/const";

export const UseUserStore = defineStore("user", {
    state: () => ({
        email: "",
        uuid: "",
        nickname:"",
        userIdx: '',
        userNickName: '',
        isLoggedIn: false,
    }),
    actions: {
        async login(user) {
            try {
                let response = await axios.post(
                    `${backend}/auth/login`
                    , user );
                if (response.status === 200) {
                    return true;
                } else{
                    return false;
                }
            } catch(error){
                console.log("로그인 실패", error);
                return false;
            }
        },

        async logout() {
            try {
                let response = await axios.post("/api/logout", {withCredentials: true });
                console.log(response)
                console.log(response.data)
                console.log(response.status)
                if (response.status === 200) {
                    this.isLoggedIn = false;
                    localStorage.removeItem("user");
                }
            } catch(error){
                console.log(" error ", error);
            }
        },

        async getAuth() {
            try{
                const response = await axios.get("/api/user/auth", {
                    withCredentials: true
                });

                console.log(response);

                return response.data.result;
            } catch (error) {
                console.log(" error ", error);
            }
        },
    },
    persist: true

})
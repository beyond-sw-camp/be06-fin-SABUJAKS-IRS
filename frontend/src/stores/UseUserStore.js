import { defineStore } from "pinia"
import axios from "axios";
import Cookies from "js-cookie";
import {jwtDecode} from "jwt-decode";
import {backend} from "@/const";

export const UseUserStore = defineStore("user", {
    state: () => ({
        email: "",
        uuid: "",
        nickname:"",
        userIdx: '',
        userName: '',
        isLoggedIn: false,
    }),
    actions: {
        async login(user) {
            try {
                console.log(backend);
                console.log(`${backend}/auth/login`);
                let response = await axios.post(
                    `${backend}/auth/login`,
                    user ,
                    {withCredentials: true}
                );
                if (response.status === 200) {
                    this.isLoggedIn = true;
                    let atoken = Cookies.get('ATOKEN');
                    if (atoken) {
                        const decoded = jwtDecode(atoken);
                        this.userIdx = decoded.idx;
                        this.userName = decoded.name;
                    }
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
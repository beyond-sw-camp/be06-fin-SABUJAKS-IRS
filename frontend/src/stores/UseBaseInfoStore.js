import { defineStore } from 'pinia';
import axios from 'axios';
import { backend } from '@/const';

export const UseBaseInfoStore = defineStore('baseInfo', {
    state: () => ({
        categories: [], // 복리후생 카테고리 및 소분류 데이터를 저장할 배열
    }),
    actions: {
        // 복리후생 카테고리 및 소분류 데이터 조회
        async fetchCategories(keyword) {
            try {
                const response = await axios.get(`${backend}/base-info/read/category?keyword=${keyword}`, {
                    headers: { 'Content-Type': 'application/json' },
                    // withCredentials: true
                });
                const data = response.data;
                this.categories = data.result; // 백엔드에서 받은 데이터를 상태에 저장
                console.log(data);
            } catch (error) {
                console.error('복리후생 카테고리 데이터를 불러오지 못했습니다:', error);
                throw new Error(error.response?.data?.message || '복리후생 카테고리 데이터 조회 실패');
            }
        },
    }
});

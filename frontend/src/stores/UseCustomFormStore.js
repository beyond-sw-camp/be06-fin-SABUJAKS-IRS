import { defineStore } from 'pinia';
import axios from 'axios';
import { backend } from '@/const';

export const UseCustomFormStore = defineStore('customform', {
    state: () => ({

    }),
    actions: {
        // 지원서 폼 저장 로직
        async saveForm(announcementIdx, selectedForms, coverLetterSections) {
            try {
                const titleList = coverLetterSections.map((section) => section.title);
                const chatLimitList = coverLetterSections.map((section) => section.characterLimit);

                const CustomFormCreateReq = {
                    announcementIdx: announcementIdx,
                    code: selectedForms,
                    titleList: titleList,
                    chatLimitList: chatLimitList
                };

                console.log('저장할 데이터:', CustomFormCreateReq);

                const dto = CustomFormCreateReq;

                // 백엔드로 POST 요청 보내기
                const response = await axios.post(`${backend}/announcement/create-step2`, dto, {
                    headers: {
                        'Content-Type': 'application/json',
                        withCredentials: true
                    },
                });
                console.log("응답" + response.data.result);

                if (response.status === 200) {
                    alert('폼 저장이 완료되었습니다. 공고 메뉴로 이동해 주세요.');

                    // router를 통해 특정 경로로 리다이렉트
                    // router.push('/recruiter/mypage');
                }
            } catch (error) {
                console.error('폼 저장 실패:', error);
                alert('폼 저장에 실패하였습니다. ' + error);
            }

        }

    }
});

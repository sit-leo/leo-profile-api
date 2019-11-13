package app.leo.profile.dto;

import java.util.List;

public class OrgIdWrapper {
    private List<IdWithNumberOfApplicantAndRecruiter> idList;

    public List<IdWithNumberOfApplicantAndRecruiter> getIdList() {
        return idList;
    }

    public void setIdList(List<IdWithNumberOfApplicantAndRecruiter> idList) {
        this.idList = idList;
    }
}

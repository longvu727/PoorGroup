class Member
{
    String memberId;
    int age;

    Member(String memberId, int age)
    {
        this.memberId = memberId;
        this.age = age;
    }

    public String getMemberId()
    {
        return memberId;
    }

    public int getAge()
    {
        return age;
    }

    public boolean equals(Object o)
    {
        // If `memberId` matches the other's one, they should be treated as the same `Member` objects.
        Member member = (Member) o;
        return this.memberId == member.memberId;
    }
}

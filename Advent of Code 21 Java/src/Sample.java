public class Sample {
    int[] BeforeRegisterStates = new int[6];
    int[] AfterRegisterStates = new int[6];

    public Sample() {
        AfterRegisterStates[0] = 0;
        AfterRegisterStates[1] = 0;
        AfterRegisterStates[2] = 0;
        AfterRegisterStates[3] = 0;
        AfterRegisterStates[4] = 0;
        AfterRegisterStates[5] = 0;
        CopyAfterToBefore();
    }

    public void CopyAfterToBefore() {
        BeforeRegisterStates[0] = AfterRegisterStates[0];
        BeforeRegisterStates[1] = AfterRegisterStates[1];
        BeforeRegisterStates[2] = AfterRegisterStates[2];
        BeforeRegisterStates[3] = AfterRegisterStates[3];
        BeforeRegisterStates[4] = AfterRegisterStates[4];
        BeforeRegisterStates[5] = AfterRegisterStates[5];
    }
}
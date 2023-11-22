package com.project.robot.repository.dto.robot

import com.project.robot.constant.enumeration.Emotion
import com.project.robot.constant.enumeration.State
import lombok.ToString

@ToString
class Robot {
    
    private var id: Long = 0;
    private var emotions: Emotion? = null;
    private var states: State = State.Idle;
    private var face: Face = Face();
    private var coordinate: Coordinate = Coordinate()

}
package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch; // 스프라이트를 그리기 위한 배치
	private Texture paddleTexture, ballTexture; // 패들과 공의 텍스처
	private Rectangle paddle, ball; // 패들과 공의 위치 및 크기 정보
	private float ballSpeedX = 200, ballSpeedY = 200; // 공의 초기 속도 설정

	@Override
	public void create() {
		batch = new SpriteBatch();
		// 리소스 로드
		paddleTexture = new Texture("paddle.png");
		ballTexture = new Texture("ball.png");
		// 패들과 공의 초기 위치 및 크기 설정
		paddle = new Rectangle(Gdx.graphics.getWidth() / 2 - 64, 20, 128, 20);
		ball = new Rectangle(Gdx.graphics.getWidth() / 2 - 8, Gdx.graphics.getHeight() / 2 - 8, 16, 16);
	}

	@Override
	public void render() {
		// 화면을 깨끗하게 지움
		ScreenUtils.clear(0, 0, 0, 1);

		// 공의 위치 업데이트
		ball.x += ballSpeedX * Gdx.graphics.getDeltaTime();
		ball.y += ballSpeedY * Gdx.graphics.getDeltaTime();

		// 공이 화면 왼쪽이나 오른쪽 끝에 도달하면 X축 속도 반전
		if (ball.x < 0 || ball.x > Gdx.graphics.getWidth() - ball.width) {
			ballSpeedX *= -1;
		}
		// 공이 화면 상단에 도달하면 Y축 속도 반전
		if (ball.y > Gdx.graphics.getHeight() - ball.height) {
			ballSpeedY *= -1;
		}
		// 공이 화면 하단에 도달하면 게임을 리셋
		if (ball.y < 0) {
			ball.setPosition(Gdx.graphics.getWidth() / 2 - 8, Gdx.graphics.getHeight() / 2 - 8);
			ballSpeedX = 200;
			ballSpeedY = 200;
		}

		// 터치 입력에 따라 패들 이동
		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			paddle.x = touchPos.x - 64; // 터치한 위치로 패들 이동(패들 중앙을 터치 위치에 맞춤)
		}

		// 공과 패들이 겹치는지 검사하여 충돌 처리
		if (ball.overlaps(paddle)) {
			ballSpeedY *= -1; // Y축 속도 반전
		}

		// 배치 시작
		batch.begin();
		// 공과 패들을 그림
		batch.draw(ballTexture, ball.x, ball.y, ball.width, ball.height);
		batch.draw(paddleTexture, paddle.x, paddle.y, paddle.width, paddle.height);
		// 배치 끝
		batch.end();
	}

	@Override
	public void dispose() {
		// 리소스 정리
		batch.dispose();
		paddleTexture.dispose();
		ballTexture.dispose();
	}
}
